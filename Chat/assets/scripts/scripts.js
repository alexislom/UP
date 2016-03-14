function run(){

	var appContainer = document.getElementsByClassName('main')[0];

	appContainer.addEventListener('click', delegateMessage);
	appContainer.addEventListener('keydown', delegateMessage);

	var username = restoreUserName() || "Default User";
	document.getElementById('NameOfUser').innerText = username;

	/*var allMessages = restore() || [ theMessage('Message 1'),
			theMessage('Message 2'),
			theMessage('Message 3')
		];
	createList(allMessages);*/
}
/////////////////////
function delegateMessage(evtObj) {
	if((evtObj.type === 'click' && evtObj.target.classList.contains('todo-button-name')) || evtObj.keyCode == 13) {
		setNewName(evtObj);
	}
	if(evtObj.type === 'click' && evtObj.target.classList.contains('todo-button'))  {
		//sendMessage(evtObj);
	}
}
////////////////////
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
	storeUserName();
	name.value = '';
}
function changeName(value){
	if(!value){
		return;
	}
	document.getElementById('NameOfUser').innerText = value;
}
function storeUserName() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	var currentUser = document.getElementById('NameOfUser').innerText;
	localStorage.setItem("username", currentUser);
}
////////////////////
function clearContents(element) {
  element.value = '';
  //element.value = document.getElementById('todoText').value;
}
////////////////////
var messageList = [];

/*function restore() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}
	var item = localStorage.getItem("localMessageList");
	return item && JSON.parse(item);
}*/
/*var theMessage = function(text) {
	var date = new Date();//.toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*//*, "$1");*/
	/*var user = localStorage.getItem("username");
	return {

		time:  date.toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*//*, "$1");*/ //date.getHours() + ":" + date.getMinutes(),
/*		sender: user,
		description: text,
		id: uniqueId()
	};*/
/*}*/
/*var uniqueId = function() {
	var date = Date.now();
	var random = Math.random() * Math.random();
	return Math.floor(date * random).toString();
};
function createList(allMessages) {
	for(var i = 0; i < allMessages.length; i++)
		addMessage(allMessages[i]);
}
function addMessage(newMes) {
	var item = createMessage(newMes);
	var items = document.getElementsByClassName('chatBox')[0];
	messageList.push(newMes);
	items.appendChild(item);
}
function createMessage(newMes){
	var divItem = document.createElement('div');
	divItem.setAttribute('id', newMes.id);
	var pr = document.createElement('span');
	var mess = document.createElement('span');
	var time = document.createTextNode (newMes.time + '  ');
	pr.appendChild (time);
	var sender = document.createTextNode(newMes.sender+ ':  ');
	pr.appendChild(sender);
	var textMessage = document.createTextNode(newMes.description);
	mess.appendChild(textMessage);
	divItem.appendChild(pr);
	divItem.appendChild(mess);
	return divItem;
}
*/


var id = 0;

function sendMessage() {
	var todoText = document.getElementById('todoText');
	//var todoText = username;
	var divItem = document.createElement('li');
	var divName = document.createElement('li');
	var textName = document.createElement('div');
	var text = document.createElement('div');
	var time = document.createElement('div');
	var but1 = document.createElement('button');
	var but2 = document.createElement('button');
	//var input = document.createElement('input');
	var textarea = document.createElement('textarea');
   		
	but1.classList.add('delBut');
	but2.classList.add('redBut');
	time.classList.add('time');
	divItem.classList.add('item');
	textName.classList.add('myName');

	time.setAttribute('id', 't');
	divItem.setAttribute('id', 'divId' + id);
	text.setAttribute('id', 'textDiv' + id);
	//input.setAttribute('id', 'textIn' + id);
	textarea.setAttribute('class', 'In');

	var s = new String(id);
	text.innerHTML = todoText.value;
	textName.innerHTML = username;
	time.textContent = new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");

	/*but1.addEventListener('click', function(){
		deleteMessage(s);
	});

	but2.addEventListener('click', function(){
		changeMessage(s);
	});*/

	divItem.appendChild(but1);
	divItem.appendChild(but2);
	divItem.appendChild(text);
	divItem.appendChild(textarea);
	divName.appendChild(time);
	divName.appendChild(textName);
	
	textarea.hidden = true;
	text.hidden = false;

	if(todoText.value != "") {
		document.getElementById('list').appendChild(divName);
		document.getElementById('list').appendChild(divItem);
	}

	/*if(username == "Sasha") {
		but1.hidden = false;
		but2.hidden = false;
	} else {
		but1.hidden = true;
		but2.hidden = true;
	}*/

	todoText.value = "";
	//id++;
}


/*function deleteMessage(id) {
	var k = document.getElementById('divId' + id);

	document.getElementById('divId' + id).innerHTML = 'Сообщение удалено...';
	k.classList.remove('item');
	k.classList.add('myDeletedMessage');
}

function changeMessage(id) {
	var k = document.getElementById('divId' + id);
	var text = document.getElementById('textDiv' + id);
	var input = document.getElementById('textIn' + id);
	input.value = text.innerHTML;
	input.hidden = false;
	text.hidden = true;

	input.addEventListener('keydown', function(e) {
		if(e.keyCode == 13) {
			text.innerHTML = input.value;
			input.hidden = true;
			text.hidden = false;
		}
	});		
}
*/











