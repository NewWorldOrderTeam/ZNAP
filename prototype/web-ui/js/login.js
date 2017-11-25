function adminLogin() {
    username = $('#username').val();
    password = $('#password').val();
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8000/api/v1.0/adminlogin/", false);
    xhr.setRequestHeader("Content-Type", "application/json");

    admin = {
      email: username,
        password: password
    };
    xhr.send(JSON.stringify(admin));

    admin_id = JSON.parse(xhr.response).id;
    console.log(admin_id);
}