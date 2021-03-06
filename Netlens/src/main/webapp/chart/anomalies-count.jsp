<!--
Copyright © 2014 Cask Data, Inc.

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
-->

<div class="table_container">
    <div class="table_title">
        Anomalies #
    </div>
    <div id="anomaliesCount" style="width: 100%; height: 75%">&nbsp;</div>
</div>

<script type="text/javascript">
    $(function() {
        reloadAnomaliesCountChart();
    });

    function reloadAnomaliesCountChart() {
        drawAnomaliesCountChart();
        setTimeout(function() {
            reloadAnomaliesCountChart();
        }, 5000);
    }

    function drawAnomaliesCountChart() {
        var startTs = Date.now() - 5000 * 120;
        var endTs = Date.now();
        $.post( "proxy/v2/apps/Netlens/procedures/AnomalyCountsProcedure/methods/count",
                        "{startTs:" + startTs + ", endTs:" + endTs + "}")
                .done(function( data ) {
                    var anomalies = JSON.parse(JSON.parse(data));
                    renderAnomaliesCountChart(anomalies);

                })
                .fail( function(xhr, textStatus, errorThrown) {
                    $('#anomaliesCount').html("<div class='server_error''>Failed to get data from server<div>");
                })
    }

    function renderAnomaliesCountChart(anomalies) {
        var data = [];
        anomalies.forEach(function(point){
            data.push([point.ts, point.value]);
        });

        var plot = $.plot("#anomaliesCount", [data], {
            series: { shadowSize: 0, bars: { show: true }},
            yaxis: { min: 0 },
            xaxis: { mode: 'time'},
            grid: {borderWidth: 0}
        });

        plot.draw();
    }

</script>