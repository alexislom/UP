var username ='';
var listOfAllMessages = [];
var id = 0;
flag = new Boolean(true);
function run(){

	var appContainer = document.getElementsByClassName('main')[0];

	appContainer.addEventListener('click', delegateMessage);
	appContainer.addEventListener('keydown', delegateMessage);

	listOfAllMessages= restoreUserMessage() || [theMessage('Chat launched')];
	id = listOfAllMessages[listOfAllMessages.length - 1].identificator;

	username = restoreUserName() || "Default User";
	document.getElementById('NameOfUser').innerText = username;

	//localStorage.clear();

	UpdateHistoryOfMessage(listOfAllMessages);

	var chatArea = document.getElementById('chatArea');
    chatArea.scrollTop += 9999;
}
/////////////////////event processing
function delegateMessage(evtObj) {
	if((evtObj.type === 'click' && evtObj.target.classList.contains('todo-button-name')) || 
		(evtObj.type === 'keydown' && evtObj.target.classList.contains('todo-input-name') && evtObj.keyCode == 13)) {
		setNewName(evtObj);
	}
	if((evtObj.type === 'click' && evtObj.target.classList.contains('todo-button')) ||
	 (evtObj.type === 'keydown' && evtObj.target.classList.contains('todo-input-message') && evtObj.keyCode == 13)){
		sendNewMessage(evtObj);
	}
}
////////////////////name processing
function restoreUserName() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	var item = localStorage.getItem("username");
	return item;
}
function setNewName(evtObj){
	var name = document.getElementById('todoName');
	changeName(name.value);
	name.value = '';
}
function changeName(value){
	if(!value){
		return;
	}
	document.getElementById('NameOfUser').innerText = value;
	username = value;
	storeUserName();
	UpdateHistoryOfMessage(listOfAllMessages);//!!!!!!!!
}
function storeUserName() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	var currentUser = document.getElementById('NameOfUser').innerText;
	localStorage.setItem("username", currentUser);
}
////////////////////some scripts processing
function clearContents(element) {
  element.value = '';
  //element.value = document.getElementById('todoText').value;
}
////////////////////message processing
function restoreUserMessage() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	var item = localStorage.getItem("localMessageList");
	return item && JSON.parse(item);
}
function theMessage(text) {
    id++;

    return {
        nickname: username,
        identificator: uniqueId(), //id,
        messtext: text,
        timer: new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1"),
        deleted: false
    };
}
var uniqueId = function() {
	var date = Date.now();
	var random = Math.random() * Math.random();
	return Math.floor(date * random).toString();
}
function sendNewMessage() {
    var todoText = document.getElementById('todoText');
    var text = todoText.value;

    if(text != "") {
    	listOfAllMessages.push(theMessage(text));
    	UpdateHistoryOfMessage(listOfAllMessages);
    	todoText.value = "";//!!!!!!!!!!!!!!!
    }

    flag = true; 

    var chatArea = document.getElementById('chatArea');
    chatArea.scrollTop += 9999;  
}
function UpdateHistoryOfMessage(listOfAllMessages) {
	document.getElementById('list').innerHTML = "";

	for(var i = 0; i < listOfAllMessages.length; i++){
		showUpdateHistoryOfMessage(listOfAllMessages[i]);
	}
	storeUserHistory(listOfAllMessages);
}

function storeUserHistory(listToSave) {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	localStorage.setItem("localMessageList", JSON.stringify(listToSave));
}

function showUpdateHistoryOfMessage(message) { 		
	var divItem = document.createElement('li');
	var divName = document.createElement('li');
	var textName = document.createElement('div');
	var textItem = document.createElement('div');
	var time = document.createElement('div');
	var chatArea = document.getElementById('chatArea');

	time.classList.add('time');
	divItem.classList.add('item');
	textItem.classList.add('Txt');
	textName.classList.add('myName');

	time.setAttribute('id', 't' + message.identificator);
	divItem.setAttribute('id', 'divId' + message.identificator);
	textItem.setAttribute('id', 'textDiv' + message.identificator);

	if(username === message.nickname) {

		if(!message.deleted) {
			var deleteBtn = document.createElement('button');
			var editBtn = document.createElement('button');
		
			deleteBtn.classList.add('delBut');
			editBtn.classList.add('redBut');
		
			deleteBtn.setAttribute('id','del' + message.identificator);
			deleteBtn.setAttribute('title','Delete message');
    		editBtn.setAttribute('id','red' + message.identificator);
    		editBtn.setAttribute('title','Click to open \nDouble-click to close');
    	
			deleteBtn.addEventListener('click', function(){
				deleteMessage(message);
			});

			editBtn.addEventListener('click', function(){
				if(flag == true) {
					changeMessage(message);
				} 
			});

			editBtn.addEventListener('dblclick', function(){
				closeEdit(message);
			});

			chatArea.addEventListener('keydown', function(e){
				if(e.keyCode == 27){
					exitFromEdit(message);
				}
			});

			divItem.appendChild(editBtn);
			divItem.appendChild(deleteBtn);
		}

		var inputForEdit = document.createElement('input');

		inputForEdit.classList.add('In');
		inputForEdit.setAttribute('id', 'textIn' + message.identificator);
		inputForEdit.hidden = true;

		divItem.appendChild(inputForEdit);
	}
	
	textItem.innerHTML = message.messtext;
	textName.innerHTML = message.nickname;
	time.innerHTML = message.timer;

	divName.appendChild(time);
	divName.appendChild(textName);

	divItem.appendChild(textItem);

	document.getElementById('list').appendChild(divName);
	document.getElementById('list').appendChild(divItem);
}

function deleteMessage(message) {
	message.messtext = 'Deleted...';
	message.deleted = true;
	flag = true;
	UpdateHistoryOfMessage(listOfAllMessages);
}

function changeMessage(message) {
	var textItem = document.getElementById('textDiv' + message.identificator);
	var inputForEdit = document.getElementById('textIn' + message.identificator);

	textItem.hidden = true;
	inputForEdit.hidden = false;
	
	inputForEdit.value = message.messtext;

	flag = false; 

	inputForEdit.addEventListener('keydown', function(e) {
		if(e.keyCode == 13 && username === message.nickname) {
			message.messtext = inputForEdit.value;
			inputForEdit.hidden = true;
			textItem.hidden = false;
			UpdateHistoryOfMessage(listOfAllMessages);
			flag = true;
		}
	});
}

function closeEdit(message) {
	var textItem = document.getElementById('textDiv' + message.identificator);
	var inputForEdit = document.getElementById('textIn' + message.identificator);
	inputForEdit.hidden = true;
	textItem.hidden = false;
	flag = true; 
}

function exitFromEdit(message) {
	var textItem = document.getElementById('textDiv' + message.identificator);
	var inputForEdit = document.getElementById('textIn' + message.identificator);
	inputForEdit.hidden = true;
	textItem.hidden = false;
	flag = true; 
}
















