 //工具箱的点击事件
        var tool=true;
        $('#tooAll').on('click',function () {
            if(tool){
                $('.tool-list').show();
                tool=!tool;
            }else {
                $('.tool-list').hide();
                tool=!tool;
            }
        })
        var measureLine = null;
        var measureArea = null;
        //测距点击事件
        document.getElementById('measureLineFunction').onclick = measureLineFunction;
        function measureLineFunction() {
            $('.tool-list').hide();
            tool=true;
            measureLine = new iGis.maptools.MeasureLine({
                gisMapObj: _map
            });
            measureLine.addMeasure();
        }

        //侧面积
        document.getElementById('measureAreaFunction').onclick = measureAreaFunction;
        function measureAreaFunction() {
            $('.tool-list').hide();
            tool = true;
            measureArea = new iGis.maptools.MeasureArea({
                gisMapObj: _map,
                layerParams: {},
                callBackFunc: function () {
                }
            });
            measureArea.addMeasure();
        }

