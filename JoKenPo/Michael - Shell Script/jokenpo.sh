#!/bin/bash

# Author: Michael de Souza Marcos
# Program: Jo Ken Po
# Date: 03/02/2017

export playerPoints=0;
export computerPoints=0;
export totalDraws=0;
export totalMatches=0;
export playerChoice=0;
export choiceA="Pedra"
export choiceB="Papel"
export choiceC="Tesoura"

function PrintResults(){
	echo ""
	echo "+--------------------------------------------"
	echo "+ Total de partidas: $totalMatches "
	echo "+ Vitorias: $playerPoints "
	echo "+ Derrotas: $computerPoints "
	echo "+ Empates: $totalDraws "
	echo "+--------------------------------------------"
}

function PrintMainScreen() {
	echo ""
	echo "+--------------------------------------------"
	echo "+           Jo Ken Po for Linux :D "
	echo "+"
	echo "+ Digite um numero e tecle enter para: "
	echo "+ 1 - Pedra "
	echo "+ 2 - Papel "
	echo "+ 3 - Tesoura "
	echo "+ 4 - Exit "
	echo "+--------------------------------------------"
}

function ReadPlayercontinueOption () {
	local fail=0;
	
	read -n1 playerChoice;
	
	if ! [[ "$playerChoice" =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
		fail=1;
	elif [ $playerChoice -lt 1 ] || [ $playerChoice -gt 4 ] ; then
		fail=1;
	fi
	
	while [ $fail -ne 0 ] ; do
		echo ""
		echo "Wrong choice, faggot."
		echo "Choose again!"
		fail=0;
		PrintMainScreen;
		ReadPlayercontinueOption;
	done
	
}

function Exit(){
	echo ""
	echo "OK, Good Bye!"
	PrintResults;
	exit 0;
}

function Continue() {
	local fail=0;
	echo "Do you wanna play again?"
	echo "1 - Yes"
	echo "2 - No"
	
	read -n1 continueOption;
	
	if ! [[ "$continueOption" =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
		fail=1;
	elif [ "$continueOption" -lt 1 ] || [ "$continueOption" -gt 2 ] ; then
		fail=1;
	fi
	
	while [ $fail -ne 0 ] ; do
		echo ""
		echo "Wrong choice, faggot."
		echo "Choose again!"
		fail=0;
		Continue;
	done
}

function ExecGame (){
	local playerChoice=${1}
	local cpuChoice=$(shuf -i1-3 -n1)
	local result=0
	
	case $playerChoice in
		1) playerMove=$choiceA
		;;
		2) playerMove=$choiceB
		;;
		3) playerMove=$choiceC
		;;
	esac
	
	case $cpuChoice in
		1) cpuMove=$choiceA
		;;
		2) cpuMove=$choiceB
		;;
		3) cpuMove=$choiceC
		;;
	esac
	
	clear;
	echo ""	
	echo "Your move: $playerMove"
	echo "Computer move: $cpuMove"

	if ( [ $playerChoice -eq 1 ] && [ $cpuChoice -eq 3 ] ) || ( [ $playerChoice -eq 2 ] && [ $cpuChoice -eq 1 ] ) || ( [ $playerChoice -eq 3 ] && [ $cpuChoice -eq 2 ] ) ; then
		result=1;
	elif [ $playerChoice -eq $cpuChoice ] ; then
		result=2;
	else
		result=3;
	fi
	
	if [ $result -eq 1 ] ; then
		echo ""
		echo "You Win!"
		((playerPoints++))
		((totalMatches++))
	elif [ $result -eq 2 ] ; then
		echo ""
		echo "F*ck, a draw!"
		((totalDraws++))
		((totalMatches++))
	else
		echo ""
		echo "Aehooo! You lose!"
		((computerPoints++))
		((totalMatches++))
	fi	
}
	
function Run() {
	PrintMainScreen;
	ReadPlayercontinueOption;
	
	if [ $playerChoice -eq 4 ] ; then
		Exit;
	else
		ExecGame $playerChoice;
	fi

	Continue;
	
	if [ $continueOption -eq 1 ]; then
		clear;
		Run;
	else
		clear;
		Exit;
	fi
};

clear;
Run;
