

function getPersonsByHobby() {
    fetch('http://localhost:8080/dat3-startcode/api/person/hobby/bage').then(function(response){
        return response.json();
    }).then(data => {
    let obj = data;
       var table = document.getElementById("persontablebody");    
       while(table.hasChildNodes()){
           table.removeChild(table.firstChild);
       }
         console.log(obj[0]);
         console.log(obj[1]);
       
    for(let i=0;i < obj.length; i++){

    var row = table.insertRow(i);
    
    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
    var cell2 = row.insertCell(2);
    var cell3 = row.insertCell(3);
    var cell4 = row.insertCell(4);
    var cell5 = row.insertCell(5);
    var cell6 = row.insertCell(6);
 
    cell0.innerHTML = obj[i].id;
    cell1.innerHTML = obj[i].email;
    cell2.innerHTML = obj[i].firstName;
    cell3.innerHTML = obj[i].lastName;
    cell4.innerHTML = "Hobby goes here";
    cell5.innerHTML = "Phone goes here";
    cell6.innerHTML = "Street/Zip/City";
        }
    });
}

function getPersonsByPhone() {
    fetch('http://localhost:8080/dat3-startcode/api/person/phone/23756493').then(function(response){
        return response.json();
    }).then(data => {
    let obj = data;
       var table = document.getElementById("persontablebody");    
       while(table.hasChildNodes()){
           table.removeChild(table.firstChild);
       }
         console.log(obj);

    var row = table.insertRow(0);
    
    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
    var cell2 = row.insertCell(2);
    var cell3 = row.insertCell(3);
    var cell4 = row.insertCell(4);
    var cell5 = row.insertCell(5);
    var cell6 = row.insertCell(6);
 
    cell0.innerHTML = obj.id;
    cell1.innerHTML = obj.email;
    cell2.innerHTML = obj.firstName;
    cell3.innerHTML = obj.lastName;
    cell4.innerHTML = "Hobby goes here";
    cell5.innerHTML = "Phone goes here";
    cell6.innerHTML = "Street/Zip/City";
        
    });
}

function getPersonsByCity() {
    fetch('http://localhost:8080/dat3-startcode/api/person/city/københavn').then(function(response){
        return response.json();
    }).then(data => {
    let obj = data;
       var table = document.getElementById("persontablebody");    
       while(table.hasChildNodes()){
           table.removeChild(table.firstChild);
       }
         console.log(obj[0]);
         console.log(obj[1]);
       
    for(let i=0;i < obj.length; i++){

    var row = table.insertRow(i);
    
    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
    var cell2 = row.insertCell(2);
    var cell3 = row.insertCell(3);
    var cell4 = row.insertCell(4);
    var cell5 = row.insertCell(5);
    var cell6 = row.insertCell(6);
 
    cell0.innerHTML = obj[i].id;
    cell1.innerHTML = obj[i].email;
    cell2.innerHTML = obj[i].firstName;
    cell3.innerHTML = obj[i].lastName;
    cell4.innerHTML = "Hobby goes here";
    cell5.innerHTML = "Phone goes here";
    cell6.innerHTML = "Street/Zip/City";
        }
    });
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

