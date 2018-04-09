//@@author marlenekoh
timetable = ["", "", "", "", "", "", "", "", "", "", "", "", "CS1231", "", "CS1010S", "", "", "CS1231", "", "CS1010S", "CS1231", "", "CS1231", "", "", "CS1231", "", "CS1231", "", "", "", "", "GET1001", "", "", "", "", "GET1001", "", "", "", "", "CS1010S", "", "", "", "", "CS1010S", "", "", "", "", "CS1010S", "", "", "", "", "CS1010S", "", "", "GET1001", "", "", "", "", "GET1001", "", "", "", "", "GET1001", "", "", "", "", "GET1001", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "CS1231", "", "", "", "", "CS1231", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
];
var myTimetable = "";
var nRows = "";
var nCells = "";
function displaySchedule(){
    for (i=0; i<nRows; i++) {
        for (n=0; n<nCells; n++) {
            myTimetable.rows[i+1].cells[n+1].lastChild.data = timetable[n+(i*nCells)];
        }
    }
}
function mapTable(){
    myTimetable = document.getElementById('myTimetable');
    nRows = myTimetable.rows.length-1;
    nCells = myTimetable.rows[0].cells.length-1;
    displaySchedule();
}
onload=mapTable;
