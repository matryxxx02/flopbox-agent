Systèmes Répartis : Agent pour le service FlopBox
----------

FERNANDES Nicolas

PHU Valentin

25/04/2021

----------

Afin de récupérer le dépôt git et de l'exécuter, suivre les commandes suivantes : 

Si vous n'avez pas installé Maven, voici un lien pour le faire : http://maven.apache.org/install.html

        $ git clone https://gitlab-etu.fil.univ-lille1.fr/phu/flopbox-agent-fernandes-phu
        $ mvn package

Pour générer la documentation

        $ mvn javadoc:javadoc

Pour lancer l'agent Flopbox

        $ mvn exec:java

----------

**Introduction**

Ce logiciel permet la communication entre un client local et un server HTTP.
Et de mettre à jour les fichiers en local avec les fichiers sur le server distant et vice-versa

Vidéo démo sur Youtube : 

----------
**Architecture**

Détection d'erreurs : 

- Throw new X

- Catch(Y)


----------

**Code Samples**