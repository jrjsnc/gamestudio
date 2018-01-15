//localStorage.setItem("tasks", staticTasks);


var stringTasks = localStorage.getItem("tasks");
var tasks = JSON.parse(stringTasks);

var taskTemplate = $("#tmplTask").html();

function addTaskToHtml(task) {
	if (task) {

		htmlCode = Mustache.render(taskTemplate, task);
		$element = $(htmlCode);
		$("#frmTasks").append($element);

		if (task.isDone) {
			$element.removeClass("activeTask");
			$element.addClass("completedTask");
		}
		// ak sa klikne na element, vykonaj funkciu
		$element.click(function() {
			$(this).toggleClass("activeTask");
			$(this).toggleClass("completedTask");
			for (var i = 0, len = tasks.length; i < len; i++) {
				if ($(this).attr('data-id') == tasks[i].id) {
					tasks[i].isDone = !tasks[i].isDone;
				}
			}
		});
	}
}

$("#btAddTask").click(function() {
	$newTaskInput = $("#inNewTask");
	var text = $newTaskInput.val().trim();
	if (text == "")
		return;
	var newTask = {
		id : Date.now(),
		task : text,
		isDone : false
	};	
	
	tasks.push(newTask);
	addTaskToHtml(newTask);
	console.log(newTask);
	$newTaskInput.val("");	
	
	if (typeof(Storage) !== "undefined") {
		localStorage.setItem("tasks", JSON.stringify(tasks));
	} else {
		window.alert("LocalStorage unsupported!");
	}	
});

$("#btRemCmpl").click(function() {
	// mazanie isDone true s filtrom a lambdou
	tasks = tasks.filter(task => !task.isDone);
	
	
	if (typeof(Storage) !== "undefined") {
		localStorage.setItem("tasks", JSON.stringify(tasks));
	} else {
		window.alert("LocalStorage unsupported!");
	}	

	$('.completedTask').remove();
});

// 1.nacitanie uloh
for (var i = 0, len = tasks.length; i < len; i++) {
	addTaskToHtml(tasks[i]);
}
/*
 * //Alternatíva predch. cyklu pre EcmaScript 6 for(var task of tasks){
 * addTaskToHtml(task); }
 */

