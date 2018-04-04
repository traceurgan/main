timetable = ["0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130", "1200", "1230", "1300", "1330", "1400", "1430", "1500", "1530", "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930", "2000", "2030", "2100", "2130", "2200", "2230", "2300", "2330"];
var isTable = "";
var nRows = "";
var nCells = "";

function dispSched(){
    for (i=0; i<nRows; i++)
    {
        for (n=0; n<nCells; n++)
        {
            isTable.rows[i+1].cells[n+1].lastChild.data = timetable[n+(i*nCells)];
        }
    }
}

function mapTable(){

    isTable = document.getElementById('myTimetable');
    nRows = isTable.rows.length-1;
    nCells = isTable.rows[0].cells.length-1;
    dispSched();
}

onload=mapTable;