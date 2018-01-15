//*****Samostatný zoznam pre každú hru.
//***** Pre každý záznam nech sa zobrazí meno a skóre.
//***** Údaje nech sa načítajú zo servera pomocou AJAX a REST.
//***** Pri neúspešnom načítaní sa zobrazí dialógové okno informujúce o chybe.
//***** Na vytvorenie zoznamov použite Mustache šablony a funkciu Mustache.render.

//***Po stlačení tlačidla sa údaje odošlú na server pomocou AJAX a REST. Pri úspešnom uložení sa
//***aktualizujú číslované zoznamy obsahujúce skóre z hier. Pri neúspešnom sa zobrazí dialógové
//***okno informujúce o chybe.

//var e = "";
var game = "";
valueFromSelect();

function valueFromSelect() {
	//e = document.getElementById("selectGame");
	//game = e.options[e.selectedIndex].text;
	game =$("#selectGame option:selected").text();	
	drawScoreTable();
}

function drawScoreTable() {
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/rest/score/" + game,
		dataType : "json"
	}

	).done(function(receivedData) {
		var scoreTemplate = $("#tmplScores").html();
		var htmlCode = Mustache.render(scoreTemplate, receivedData);
		$("#scoreTable").html(htmlCode);
		// $("#scoreTable").html(Mustache.render($("#tmplScores").html(),
		// receivedData));
	}).fail(function() {
		$("#scoreTable").html("<p>Sorry, unable to get data</p>");
	});
}

$("#btSaveScore").click(function() {
	var score = parseInt($("#inNewScore").val().trim());
	var username = $("#inUsername").val().trim();
	console.log(score);
	console.log(username);

	if (isNaN(score)) {
		window.alert("You have to enter some number as score.");
		return;
	}
	
	if (username == "") {
		window.alert("Username cannot be empty.");
		return;
	}

	var data2send = {
		"username" : username,
		"game" : game,
		"value" : score
	}

	$.ajax({
		type : "POST",
		url : "http://localhost:8080/rest/score",
		contentType : "application/json",
		data : JSON.stringify(data2send)
	}

	).done(function() {
		drawScoreTable();
	}).fail(function() {
		window.alert("Unable to send data!");
	});
}

);

$("#inUsername").keyup(function(event) {
    if (event.keyCode === 13) {
        $("#btSaveScore").click();
    }
});