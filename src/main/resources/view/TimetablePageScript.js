timetable = ["", "", "", "", "", "", "", "", "", "", "", "", "", "CS2103T", "", "", "", "", "CS2103T", "", "", "", "", "CS3241", "", "", "", "", "CS3241", "", "", "", "", "CS3241", "", "", "", "", "CS3241", "", "CS2101", "", "CS3230", "CS2101", "", "CS2101", "", "CS3230", "CS2101", "", "CS2101", "", "CS3230", "CS2101", "", "CS2101", "", "CS3230", "CS2101", "", "CS3247", "CS3241", "", "", "", "CS3247", "CS3241", "", "", "", "CS3247", "", "", "CS3241", "CS3230", "CS3247", "", "", "CS3241", "CS3230", "GES1021", "CS3247", "", "GES1021", "CS2101", "GES1021", "CS3247", "", "GES1021", "CS2101", "GES1021", "CS3247", "", "GES1021", "CS2101", "GES1021", "CS3247", "", "GES1021", "CS2101", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
];
var isTable = "";
var nRows = "";
var nCells = "";
function dispSched(){
    for (i=0; i<nRows; i++) {
        for (n=0; n<nCells; n++) {
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
