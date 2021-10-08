function getPersonsByHobby() {
fetch("http://localhost:8080/dat3-startcode/api/person/hobby/bage")
  .then(function(response) {
    return response.json();
   })
    .then(function(data) {
    console.log(data);
    let obj = JSON.parse(data);
    
    createTable();
    console.log(obj);
    cell1.innerHTML = obj[0];
   });
}

function createTable(){
var table = document.getElementById("persontable");
var row = table.insertRow(1);
var row1 = table.insertRow(2);

var cell1 = row.insertCell(0);
var cell2 = row.insertCell(1);


cell2.innerHTML = "NEW CELL2";
}

//
//let options = {
//   method: "POST",
//   headers: {
//   'Accept': 'application/json',
//   'Content-Type': 'application/json'
//   },
//   body: JSON.stringify({
//     email: "lis@lis.com",
//     firstName: "Lis Benson",
//     lastName: "Benson"
//   })
//};
//fetch("http://localhost:8080/dat3-startcode/api/person/create",options);

