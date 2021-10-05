function getPersonsByHobby() {
fetch("http://localhost:8080/dat3-startcode/api/person/hobby/bage")
  .then(function(response) {
    return response.json();
   })
    .then(function(data) {
    console.log(data);
    let obj = JSON.parse(data);
   });
}

obj.email[0];


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

