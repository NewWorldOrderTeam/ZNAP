function adminLogin() {
    username = $('#username').val();
    password = $('#password').val();
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "https://znap.pythonanywhere.com/api/v1.0/adminlogin/", false);
    xhr.setRequestHeader("Content-Type", "application/json");

    admin = {
      email: username,
        password: password
    };
    xhr.send(JSON.stringify(admin));

    admin_id = JSON.parse(xhr.response).id;
    console.log(admin_id);
    znap_id = JSON.parse(xhr.response).znap_id;
    console.log(znap_id);
    localStorage.setItem('admin_id', admin_id);
    localStorage.setItem('znap_id', znap_id);

    window.location.href="../web-ui/pages/users.html";
}