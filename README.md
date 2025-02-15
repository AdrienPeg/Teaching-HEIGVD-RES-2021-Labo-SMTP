# Teaching-HEIGVD-RES-2021-Labo-SMTP

Par Michael Ruckstuhl et Adrien Peguiron

## Description

Ce projet a pour but de générer des e-mails à partir de fausses adresses pour faire des blagues à vos amis. Les messages sont envoyés à des groupes d'au moins trois personnes dont une est aléatoirement choisie pour envoyer le mail.  Vous pouvez entièrement customiser l'adresse d'envoi ainsi que les destinataires et les messages envoyés. Le mieux dans tout ça, vous victimes ne sauront jamais que c'est vous derrière le mail du prince du nigeria leur offrant 50 millions. 


## Mise en place d'un serveur "mock SMTP"

Il peut-être pratique de tester les fonctionnalité du programme sur un serveur mock. Pour le mettre en place, rien de plus simple. Il faut d'abord vous assurez d'avoir Java 7 runtime environment sur votre machine. Si ce n'est pas le cas, vous pouvez simplement le télécharger [ici](http://www.oracle.com/technetwork/java/javase/downloads/java-se-jre-7-download-432155.html). 

Ensuite, il suffit de télécharger MockMock avec ce [lien](https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true).

Une fois MockMock installé, ouvrez une invite de commande et rendez-vous dans le répértoire où il a été téléchargé et entrez la commande suivante : `java -jar MockMock.jar` 

MockMock est désormais lancé et prêt à être utilisé sur le port 25 pour SMTP et sur le port 8282 pour l'interface web. Vous pouvez accéder à l'interface web en entrant l'url suivante sur votre navigateur : `localhost:8282`. 

Si vous souhaitez changer les port SMTP et HTTP, vous pouvez le faire au lancement : `java -jar MockMock.jar -p SmtpPort -h HttpPort`

Sources : https://github.com/tweakers/MockMock

## Mise en place d'un serveur "mock SMTP" via Docker

Dans le dossier docker se trouve un jar permettant dans lancer MockMock qui écoutera sur le port 25 et écrira les mails sur le port 8282

Pour le lancer, dans le dossier "docker" en ligne de commande, taper les commandes suivantes:

> docker build --tag monmock .  
> docker run -p 25:25 8282:8282 monmock

Lorsque vous lancerez le labo SMTP, les mail reçu sont visble sur le http://localhost:8282/ de la machine host.

## Utiliser le programme.

Avant de lancer le programme et de piéger vos amis, quelques manipulations sont nécessaires. La première étant de cloner ce repo sur votre machine. Vous pouvez le faire avec la commande suivante : `git clone https://github.com/AdrienPeg/Teaching-HEIGVD-RES-2021-Labo-SMTP.git SmtpPrank`

Dirigez-vous ensuite dans votre nouveau dossier "SmtpPrank" et ouvrez le dossier config. Ici, vous trouverez 3 fichiers de configuration à remplir par vos soins.

- Le fichier config.properties vous permet de déterminer l'adresse et le port du serveur SMTP, le nombre de groupes à pranker et les témoins à mettre en copie.

- Le fichier messages.utf8 contient les messages à envoyer à vos victimes. ceux-ci doivent être séparés par "==".
- Le fichier victims.utf8 contient les adresses mails des futurs prankés. Attention ! il est nécessaire d'avoir au moins 3 personnes par groupe. Si vous avez choisis d'envoyer vos messages à 8 groupes, il vous faut au minimum 24 adresses mails différentes. Il est également nécessaire d'avoir un message par groupe.

Une fois les 3 fichiers modifiés comme bon vous semble, ouvrez le dossier "SmtpPrank" en tant que projet sur un éditeur de code, et lancez la fonction main se trouvant dans src/main/java/Main.java. 


## Description du code

Ce projet comporte 8 classes et 2 interfaces. Les détails de chaque classe sont indiqués ci-dessous

<img width="603" alt="uml" src="https://user-images.githubusercontent.com/59923079/116816253-75b67000-ab61-11eb-9af4-701742eb85db.png">

Les fonctions getTo et getCC retournent un tableau de String, mais le logiciel de création d'UML n'acceptait pas les "[]" comme retour de fonction. Il n'acceptait également pas les "<>" comme paramètres de fonction. C'est pourquoi à certains endroit des tirets sont utilisés.

Nous avons réalisé ce projet en nous aidant du cours, plus particulièrement de la [4ème vidéo de présentation du laboratoire](https://www.youtube.com/watch?v=OrSdRCt_6YQ). 

- ConfigurationManager 

Cette classe permet de récupérer toutes les informations importantes pour créer les pranks. Son but est de récupérer les données enregistrées dans les fichiers de configurations pour ensuite les transférer à PrankGenerator. ConfigurationManager implémente l'interface IConfigurationManager

- PrankGenerator

Cette classe se charge de créer les groupes qui seront piégés ainsi que de créer une prank pour chaque groupe.

- SmtpClient

Cette classe permet d'envoyer les mails de prank avec le protocole SMTP puis de fermer la connexion SMTP. SmtpClient implémente l'interface ISmtpClient.

- Main

C'est ici qu'est executé le programme. Un configuration manager, prank generator et smtp client sont créés, le prank generator fourni alors une liste de pranks qui seront envoyées par mail grâce au smtp client.

- Prank

Cette classe crée les mails piégés. L'envoyeur, le récepteur, les copies carbones, le sujet ainsi que le corps du mail sont définis ici.

- Groupe

Définit les méthodes pour créer un groupe contenant des personnes.

- Personne

Cette classe représente les personnes à pranker.

- Message     

Cette classe permets de modifier ou récupérer certaines parties spécifiques d'un mail. Ces parties sont le sujet, le corps, l'envoyeur, les destinataire et les copies carbones.

## Dialogue entre le client et un serveur SMTP

Une fois le client connecté au serveur, ce dernier va envoyer un message indiquant son nom et sa version ainsi que le nom du client connecté (ici le nom de mon ordinateur est DESKTOP-7PTJ0V8) : <img width="351" alt="discussion1" src="https://user-images.githubusercontent.com/59923079/116818294-ee6dfa00-ab6a-11eb-9878-51e5d6da096d.png">
 
Ensuite, à chaque fois que des données sont enregistrées par le serveur comme un destinataire du mail ou le sujet, le serveur indique que c'est bien enregistré : <img width="49" alt="discussion2" src="https://user-images.githubusercontent.com/59923079/116818391-59b7cc00-ab6b-11eb-99b0-ebc45a5e15af.png">

Finalement, une fois le mail terminé avec la suite de caractères "\r\n.\r\n", le serveur indique que l'enregistrement des données est terminé : <img width="221" alt="discussion3" src="https://user-images.githubusercontent.com/59923079/116818429-8cfa5b00-ab6b-11eb-930b-c2cddcbf74cb.png">
