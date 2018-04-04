timetable = ["", "", "", "", "", "", "", "", "", "", "", "", "CS1231", "", "CS1010S", "", "", "CS1231", "", "CS1010S", "CS1231", "", "CS1231", "MA1101R", "", "CS1231", "", "CS1231", "MA1101R", "", "", "", "MA1101R", "", "", "", "", "MA1101R", "", "", "", "", "CS1010S", "", "", "", "", "CS1010S", "", "", "", "", "CS1010S", "", "", "", "", "CS1010S", "", "", "GET1001", "MA1101R", "", "", "MA1101R", "GET1001", "MA1101R", "", "", "MA1101R", "GET1001", "MA1101R", "", "", "MA1101R", "GET1001", "MA1101R", "", "", "MA1101R", "", "GET1001", "", "", "", "", "GET1001", "", "", "", "", "GET1001", "", "", "CS1231", "", "GET1001", "", "", "CS1231", "", "", "", "", "", "", "", "", "", "", "", "", "GER1000", "", "", "", "", "GER1000", "", "", "", "", "GER1000", "", "", "", "", "GER1000", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
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
