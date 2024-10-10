let diplomeCounter = 1;
function ajouterDiplome() {
    diplomeCounter++;
    const container = document.getElementById('diplome-container');
    const div = document.createElement('div'); 
    const inputText = document.createElement('input');
    inputText.type = 'text';
    inputText.name = 'Diplome' + diplomeCounter;
    inputText.placeholder = 'Diplome' + diplomeCounter;
    const inputNumber = document.createElement('input');
    inputNumber.type = 'number';
    inputNumber.name = 'Note' + diplomeCounter;
    inputNumber.placeholder = 'Note' + diplomeCounter;
    div.appendChild(inputText);
    div.appendChild(inputNumber);
    container.appendChild(div);
}

let expCounter = 1;
function ajouterExperience() {
    expCounter++;
    const container = document.getElementById('experience-container');
    const div = document.createElement('div'); 
    const inputText = document.createElement('input');
    inputText.type = 'text';
    inputText.name = 'Experience' + expCounter;
    inputText.placeholder = 'Experience' + expCounter;
    const inputNumber = document.createElement('input');
    inputNumber.type = 'number';
    inputNumber.name = 'Point' + expCounter;
    inputNumber.placeholder = 'Point' + expCounter;
    div.appendChild(inputText);
    div.appendChild(inputNumber);
    container.appendChild(div);
}

let rps =1;
function ajouterReponse(){
    rps++;
    const container=document.getElementById('reponse-container');
    const div = document.createElement('div');
    div.style.cssText=container.firstChild;
    div.classList.add('row');
    const inputText=document.createElement('input');
    inputText.type='text';
    inputText.name='Reponse' + rps; 
    inputText.style.cssText='margin-left:18%; width:48%; margin-top:2%';
    inputText.placeholder = 'Reponse' + rps;
    const selectRep = document.createElement('select');
    const optionVrai = document.createElement('option');
    const optionFaux = document.createElement('option');
    selectRep.name='TypeReponse' + rps;
    selectRep.style.cssText='margin-left:8%; width:16%; height :25px';
    optionVrai.textContent='Vrai';
    optionFaux.textContent='Faux';
    optionVrai.value='1';
    optionFaux.value='0';
    selectRep.appendChild(optionVrai);
    selectRep.appendChild(optionFaux);
    div.appendChild(inputText);
    div.appendChild(selectRep);
    container.appendChild(div);
}

