const app = new Vue({
    el: '#app',
    data(){
        return{
            active:0,
            dataForm:{
                host:'localhost',
                port:'3306',
                user:'root',
                password:'123456',
                dataBase:''
            }
        }
    },
    methods:{
        getDbConn(){
            $.ajax({
                type: 'POST',
                url: '/db/getDbConn',
                data: JSON.stringify({
                    "host":this.dataForm.host,
                    "port":this.dataForm.port,
                    "user":this.dataForm.user,
                    "password":this.dataForm.password,
                    "dataBase":this.dataForm.dataBase,
                }),
                contentType:"application/json",
                async: false,
                dataType: 'json',
                success(data) {
                    if (data && data.code === 0) {
                        console.log(data);
                    }
                }
            });
        }
    }

})