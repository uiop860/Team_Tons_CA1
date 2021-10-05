function getPersonsByHobby() {
document.getElementById(hobbyInput.valueOf());
fetch("http://localhost:8080/dat3-startcode/api/person/hobby/"+hobbyInputhobbyInput.valueOf())
  .then(function(response) {
    return response.json();
   })
    .then(function(data) {
    console.log(data);
    let obj = JSON.parse(data);
   });
}

let options = {
   method: "POST",
   headers: {
   'Accept': 'application/json',
   'Content-Type': 'application/json'
   },
   body: JSON.stringify({
     email: "lis@lis.com",
     firstName: "Lis Benson",
     lastName: "Benson"
   })
};
fetch("http://localhost:8080/dat3-startcode/api/person/create",options);

