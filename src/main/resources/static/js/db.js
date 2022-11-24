const app = new Vue({
    el: '#app',
    data() {
        return {
            active: 0,
            dataForm: {
                host: 'localhost',
                port: '3306',
                user: 'root',
                password: '123456',
                dataBase: ''
            },
            emptyVisible: true,
            table: '',
            tables: [],
            checkTables: [],
            tableSchema: [],
            dataList: [],
            pageIndex: 1,
            pageSize: 10,
            totalPage: 0,
            dataListLoading: false,
            dataListSelections: [],
            //菜单上方配置项是否可见
            configVisible: true,
            //添加修改dialog
            addOrUpdateVisible: false,
            schemaAddOrUpdateVisible: false,
            keyIndex: 0,
            primaryKey: undefined,
            addOrUpdateForm: [],
            schemaFlag: false,
            schemaForm: {},
            typeOptions:[
                "bigint",
                "binary",
                "bit",
                "blob",
                "char",
                "date",
                "datetime",
                "decimal",
                "double",
                "enum",
                "float",
                "geometry",
                "geometrycollection",
                "int",
                "integer",
                "json",
                "linestring",
                "longblob",
                "linestring",
                "longtext",
                "mediumblob",
                "mediumint",
                "mediumtext",
                "multilinestring",
                "multipoint",
                "multipolygon",
                "numeric",
                "point",
                "polygon",
                "real",
                "set",
                "smallint",
                "text",
                "time",
                "timestamp",
                "tinyblob",
                "tinyint",
                "tinytext",
                "varbinary",
                "varchar",
                "year"
            ]
        }
    },
    methods: {
        getDbConn() {
            $.ajax({
                type: 'POST',
                url: '/db/getDbConn',
                data: JSON.stringify({
                    "host": this.dataForm.host,
                    "port": this.dataForm.port,
                    "user": this.dataForm.user,
                    "password": this.dataForm.password,
                    "dataBase": this.dataForm.dataBase
                }),
                contentType: "application/json",
                async: true,
                dataType: 'json',
                success(data) {
                    if (data && data.code === 0) {
                        const map = data.map
                        for (const mapKey in map) {
                            app.tables = map[mapKey].map(item => {
                                return {
                                    label: item,
                                    key: item
                                }
                            })
                        }
                    } else {
                        app.$message.error(data.msg)
                    }
                }
            });
        },
        /**
         * 获取表结构
         */
        getTableSchema() {
            app.tableSchema=[]
            $.ajax({
                type: 'GET',
                url: '/db/getTableSchema',
                data: {
                    "dataBase": app.dataForm.dataBase,
                    "tableName": app.table
                },
                contentType: "application/json",
                async: true,
                dataType: 'json',
                success(data) {
                    if (data && data.code === 0) {
                        const map = data.map;
                        const childList = []
                        for (const child in map) {
                            childList.push(child)
                        }
                        for (let i = 0; i < map['Field'].length; i++) {
                            let obj = Object.create(null);
                            for (let j = 0; j < childList.length; j++) {
                                obj[childList[j]] = map[childList[j]][i];
                            }
                            app.tableSchema.push(obj)
                        }
                    } else {
                        app.$message.error(data.msg)
                    }
                }
            });
        },
        /**
         * 下拉框选中值发生变化
         */
        tableChange() {
            app.getTableSchema()
        },
        handleClick(tab, _event) {
            app.emptyVisible = false
            app.table = app.checkTables[tab.index]
            //重置页码
            app.pageIndex = 1
            this.getTableSchema()
            this.getDataList()
        },
        // 获取数据列表
        getDataList() {
            app.dataList = []
            app.dataListLoading = true
            $.ajax({
                type: 'GET',
                url: '/db/getDataList',
                data: {
                    'dataBase': app.dataForm.dataBase,
                    'tableName': app.table,
                    'page': app.pageIndex,
                    'limit': app.pageSize
                },
                contentType: "application/json",
                async: true,
                dataType: 'json',
                success(data) {
                    if (data && data.code === 0) {
                        const total = parseInt(data.map.total['count(1)'][0]);
                        app.totalPage = total
                        if (total > 0) {
                            const map = data.map.data;
                            const columnNames = app.tableSchema.map(item => {
                                return item.Field
                            })
                            let length = map[columnNames[0]].length;
                            if (length > 0) {
                                for (let i = 0; i < length; i++) {
                                    let obj = Object.create(null);
                                    for (const key of columnNames) {
                                        obj[key] = key
                                    }
                                    for (let j = 0; j < columnNames.length; j++) {
                                        obj[columnNames[j]] = map[columnNames[j]][i];
                                    }
                                    app.dataList.push(obj)
                                }
                            }
                        }
                        app.dataListLoading = false
                    } else {
                        app.$message.error(data.msg)
                    }
                }
            });

        },
        // 每页数
        sizeChangeHandle(val) {
            app.pageSize = val
            app.pageIndex = 1
            app.getDataList()
        },
        // 当前页
        currentChangeHandle(val) {
            app.pageIndex = val
            app.getDataList()
        },
        addOrUpdateHandle(row) {
            app.keyIndex = undefined
            const columnNames = app.tableSchema.map(item => {
                return item.Field
            })
            const remarkNames = app.tableSchema.map(item => {
                return item.Comment
            })
            app.addOrUpdateForm = []
            if (row) {
                //获取主键
                const keyRow = this.tableSchema.filter(item => {
                    if (item.Key === 'PRI') {
                        return true
                    }
                })
                if (keyRow.length === 0) {
                    app.$message.error("该表无主键，请检查!")
                    return
                }
                app.primaryKey = keyRow[0].Field
                app.keyIndex = row[app.primaryKey]
                $.ajax({
                    type: 'GET',
                    url: '/db/info',
                    data: {
                        'dataBase': app.dataForm.dataBase,
                        'tableName': app.table,
                        'primaryKey': app.primaryKey,
                        'keyVal': app.keyIndex,
                    },
                    contentType: "application/json",
                    async: true,
                    dataType: 'json',
                    success(data) {
                        if (data && data.code === 0) {
                            //对结构进行封装
                            const map = data.map;

                            for (let j = 0; j < columnNames.length; j++) {
                                app.addOrUpdateForm.push({
                                    "key": remarkNames[j],
                                    "value": map[columnNames[j]][0],
                                })
                            }
                        }
                    }
                })
            } else {
                for (let j = 0; j < columnNames.length; j++) {
                    app.addOrUpdateForm.push({
                        "key": remarkNames[j],
                        "value": "",
                    })
                }
            }
            this.addOrUpdateVisible = true;
        },
        schemaAddOrUpdateHandle(row) {
            this.schemaForm = {}
            if (row) {
                //深拷贝
                const clone = JSON.parse(JSON.stringify(row))
                app.schemaFlag = true
                app.schemaForm = clone
                if (clone.Type.includes("(")) {
                    const type = clone.Type
                    let start = type.indexOf("(") + 1;
                    let end = type.indexOf(")");
                    app.schemaForm.Size = type.substring(start, end)
                    app.schemaForm.Type = type.substring(0, start - 1)
                }
                app.schemaForm.oldField=row.Field
                app.schemaForm.oldKey=row.Key
            }else{
                app.schemaForm.Size=""
            }
            app.schemaAddOrUpdateVisible = true
        },
        addOrUpdateSubmit() {
            //找到remark对应的字段
            const columnNames = app.tableSchema.map(item => {
                return item.Field
            })
            const submitForm = []
            for (let j = 0; j < app.addOrUpdateForm.length; j++) {
                submitForm.push({
                    "key": columnNames[j],
                    "value": app.addOrUpdateForm[j].value,
                })
            }
            $.ajax({
                type: 'POST',
                url: `/db/${app.keyIndex ? 'update' : 'save'}`,
                data: JSON.stringify({
                    'dataBase': app.dataForm.dataBase,
                    'tableName': app.table,
                    'primaryKey': app.primaryKey,
                    'keyVal': app.keyIndex,
                    "data": JSON.stringify(submitForm),
                }),
                contentType: "application/json",
                async: true,
                dataType: 'json',
                success(data) {
                    if (data && data.code === 0) {
                        app.$message.success("操作成功")
                        app.addOrUpdateVisible = false
                        app.getDataList()
                    } else {
                        app.$message.error(data.msg)
                    }
                }
            });
        },
        schemaAddOrUpdateSubmit() {
            $.ajax({
                type: 'POST',
                url: `/schema/${app.schemaFlag ? 'update' : 'save'}`,
                data: JSON.stringify({
                    'dataBase': app.dataForm.dataBase,
                    'tableName': app.table,
                    'oldField': app.schemaForm.oldField,
                    'Field': app.schemaForm.Field,
                    'Type': app.schemaForm.Size?`${app.schemaForm.Type}(${app.schemaForm.Size})`:app.schemaForm.Type,
                    "Null": app.schemaForm.Null,
                    "Key": app.schemaForm.Key,
                    "oldKey": app.schemaForm.oldKey,
                    "Default": app.schemaForm.Default,
                    "Extra": app.schemaForm.Extra,
                    "Comment": app.schemaForm.Comment
                }),
                contentType: "application/json",
                async: true,
                dataType: 'json',
                success(data) {
                    if (data && data.code === 0) {
                        app.$message.success("操作成功")
                        app.schemaAddOrUpdateVisible = false
                        app.getTableSchema()
                        app.getDataList()
                    } else {
                        app.$message.error(data.msg)
                    }
                }
            });
        },
        // 多选
        selectionChangeHandle(val) {
            this.dataListSelections = val
        },
        deleteHandle(row) {
            //获取主键
            const keyRow = this.tableSchema.filter(item => {
                if (item.Key === 'PRI') {
                    return true
                }
            })
            if (keyRow.length === 0) {
                app.$message.error("该表无主键，请检查!")
                return
            }
            app.primaryKey = keyRow[0].Field
            if (row) {
                app.keyIndex = row[app.primaryKey]
            }
            const primaryKeys = row ? [app.keyIndex] : this.dataListSelections.map(item => {
                return item[app.primaryKey]
            })
            app.$confirm(`确定对[${app.primaryKey}=${primaryKeys.join(',')}]进行[${primaryKeys.length === 1 ? '删除' : '批量删除'}]操作?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                $.ajax({
                    type: 'POST',
                    url: `/db/delete`,
                    data: JSON.stringify({
                        'dataBase': app.dataForm.dataBase,
                        'tableName': app.table,
                        'primaryKey': app.primaryKey,
                        'keyList': JSON.stringify(primaryKeys)
                    }),
                    contentType: "application/json",
                    async: true,
                    dataType: 'json',
                    success(data) {
                        if (data && data.code === 0) {
                            app.$message.success("操作成功")
                            app.addOrUpdateVisible = false
                            app.getDataList()
                        } else {
                            app.$message.error(data.msg)
                        }
                    }
                });
            }).catch(() => {
            })
        },
        schemaDeleteHandle(row) {

        }
    }

})