#!/usr/bin/env python
# -*- coding: utf-8 -*-

import pygame
import time
import random

pygame.init()

displayWidth = 800
displayHeight = 600

largeText = pygame.font.Font('SilverAgeQueens.ttf', 82)
regularText = pygame.font.Font('SilverAgeQueens.ttf', 24)

gameDisplay = pygame.display.set_mode((displayWidth, displayHeight))
pygame.display.set_caption('Rock Paper Scissors')
clock = pygame.time.Clock()
gameExit = False

bgColor = (255, 253, 231)
fgColor = (33, 33, 33)
fgColorFocus = (0, 60, 255)
fgSelectedColor = (255, 0, 60)
fgHoverColor = (66, 66, 66)

selected = None

options = ['Rock', 'Paper', 'Scissors']

class Symbol(object):

    def __init__(self, surf, name, x, y):
        self.name = name
        self.surf = surf
        self.x = x
        self.y = y
        self.margin = 20

        if name == "Rock":
            self.playable = imgRock
        elif name == "Paper":
            self.playable = imgPaper
        else:
            self.playable = imgScissors

    def show(self):
        global selected

        mouse = pygame.mouse.get_pos()
        click = pygame.mouse.get_pressed()
        if (self.x + baseImgWidth) > mouse[0] > self.x and  (self.y + baseImgHeight) > mouse[1] > self.y:
            self.box(fgColorFocus)
            if click[0] == 1:
                self.select()
        else:
            if self.name == selected:
                self.box(fgSelectedColor)
            else:
                self.box(fgColor)

        self.surf.blit(self.playable, (self.x, self.y))

    def box(self, color):
        pygame.draw.aalines(self.surf, color, True, (
            (self.x-self.margin, self.y-self.margin),
            ((self.x-self.margin)+(baseImgWidth+self.margin), self.y-self.margin),
            ((self.x-self.margin)+(baseImgWidth+self.margin), ((self.y-self.margin)+(baseImgHeight+self.margin))),
            ((self.x-self.margin),((self.y-self.margin)+(baseImgHeight+self.margin)))
        ))

    def select(self):
        global selected
        selected = self.name
        self.box(fgSelectedColor)

imgRock = pygame.image.load('rock.1.png')
imgPaper = pygame.image.load('paper.1.png')
imgScissors = pygame.image.load('scissors.1.png')

baseImgWidth = 150
baseImgHeight = 150

basex = 160
basey = 380

rounds = 0
wins = 0
ties = 0

rock = Symbol(gameDisplay, "Rock", basex, basey)
paper = Symbol(gameDisplay, "Paper", (basex+baseImgWidth+30), basey)
scissors = Symbol(gameDisplay, "Scissors", (basex+(baseImgWidth*2)+60), basey)

def score(wins, rounds):
    text = regularText.render("Rounds: " + str(rounds), True, fgColor)
    gameDisplay.blit(text, (10,10))
    text = regularText.render("Wins: " + str(wins), True, fgColor)
    gameDisplay.blit(text, (10,42))
    text = regularText.render("Ties: " + str(ties), True, fgColor)
    gameDisplay.blit(text, (10,74))

def textObjects(text, font, color):
    textSurface = font.render(text, True, color)
    return textSurface, textSurface.get_rect()

def showMessage(text):
    TextSurf, TextRect = textObjects(text, largeText, fgColor)
    TextRect.center = ((displayWidth/2), (displayHeight/2))
    gameDisplay.blit(TextSurf, TextRect)
    pygame.display.update()

def quitGame():
    pygame.quit()
    quit()

def button(msg, x, y, w, h, c, fc, action=None):
    mouse = pygame.mouse.get_pos()
    click = pygame.mouse.get_pressed()

    if x + w > mouse[0] > x and y + h > mouse[1] > y:
        pygame.draw.rect(gameDisplay, fc, (x, y, w, h))
        if click[0] == 1 and action != None:
            action()
    else:
        pygame.draw.rect(gameDisplay, c, (x, y, w, h))

    textSurf, textRect = textObjects(msg, regularText, bgColor)
    textRect.center = ((x + (w / 2)),(y + (h / 2)))
    gameDisplay.blit(textSurf, textRect)

def gameIntro():
    intro = True
    while intro:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                quitGame()
        gameDisplay.fill(bgColor)
        showMessage('Rock Paper Scissors')

        button("Start", 150, 450, 100, 50, fgColor, fgHoverColor, gameLoop)
        button("Quit", 550, 450, 100, 50, fgColor, fgHoverColor, quitGame)

        pygame.display.update()
        clock.tick(15)

def play():
    global rounds
    global wins
    global ties
    choice = random.choice(options)
    computer = Symbol(gameDisplay, choice, 350, 80)
    computer.show()

    if choice == selected:
        showMessage('Tie :/')
        ties += 1
    elif (choice == "Rock" and selected == "Paper") or (choice == "Paper" and selected == "Scissors") or (choice == "Scissors" and selected == "Rock"):
        showMessage('You won! \o/')
        wins += 1
    else:
        showMessage('You lost, human.')
    rounds += 1
    time.sleep(2)

def gameLoop():
    global rock
    global paper
    global scissors
    global rounds
    global wins
    global ties

    rounds = 0
    wins = 0
    ties = 0
    while not gameExit:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                quitGame()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_1:
                    rock.select()
                if event.key == pygame.K_2:
                    paper.select()
                if event.key == pygame.K_3:
                    scissors.select()
                if event.key == pygame.K_RETURN:
                    play()

        gameDisplay.fill(bgColor)
        score(wins, rounds)

        rock.show()
        paper.show()
        scissors.show()

        button("Go!", 320, 550, 170, 50, fgColor, fgHoverColor, play)
        button("Quit", 700, 550, 100, 50, fgColor, fgHoverColor, quitGame)

        pygame.display.update()
        clock.tick(15)

gameIntro()
gameLoop()
quitGame()
