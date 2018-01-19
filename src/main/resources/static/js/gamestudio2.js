function validateCommentForm() {
	var a = document.forms["form"]["content"].value;
	if (a == null || a == "") {
		window.alert("Please Fill All Required Fields");
		return false;
	}
}

document.querySelector('.hello').scrollIntoView({ behavior: 'smooth' });



var password = document.getElementById("password")
var confirm_password = document.getElementById("confirm_password");
var email = document.getElementById("email");


function validatePassword(){
if(password.value != confirm_password.value) {
	window.alert("Passwords dont match");
} else {
  confirm_password.setCustomValidity('');
}
}

//password.onchange = validatePassword;
//confirm_password.onkeyup = validatePassword;


function validateEmail() {
  var x = document.forms["myForm"]["email"].value;
  var atpos = x.indexOf("@");
  var dotpos = x.lastIndexOf(".");
  if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length) {
      window.alert("Not a valid e-mail address");
      return false;
  }
}

function validate(){
	validateEmail();
	validatePassword();
}