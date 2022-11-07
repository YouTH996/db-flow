const app = new Vue({
    el: '#app',
    data(){
        return{
            map:null
        }
    },
    created(){
        this.getDataList()

    },
    mounted(){
      this.initMap()
    },
    methods:{
        getDataList(){
            console.log(1);
        },
        initMap(){
            this.map = new ol.Map({
                target: 'map',
                view: new ol.View({
                    center: [121.1265, 33.3186],
                    zoom: 6,
                    projection:'EPSG:4326'

                }),
                layers:[
                    new ol.layer.Tile({
                        source: new ol.source.XYZ({
                            url: "https://webrd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}"
                        }),
                    })
                ]
            })
        }
    }

})