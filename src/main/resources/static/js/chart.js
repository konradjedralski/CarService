google.charts.load('current', {'packages': ['line']});
google.charts.setOnLoadCallback(drawChart);

async function drawChart() {
    let response = await fetch('/combustion-rest/chart');
    let chartData = await response.json();
    console.log(chartData);

    const table = chartData.map((element) => {
        return [element.month, element.combustionValue];
});

    console.log({table});

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Miesiąc');
    data.addColumn('number', 'Spalanie');

    data.addRows(table);

    var options = {
        chart: {
            title: 'Wykres miesięcznego spalania'
        },
        width: 775,
        height: 450
    };

    var chart = new google.charts.Line(document.getElementById('linechart_material'));

    chart.draw(data, google.charts.Line.convertOptions(options));
}