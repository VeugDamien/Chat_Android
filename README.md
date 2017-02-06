# AndroidProject
Projet Android Master

Damien VEUGEOIS

Application ""Parlez Vous"" basée sur l'API https://github.com/loicortola/training-chat-rest version 2

Dépendance implémenté:
- GSON
- OkHTTP
- Picasso
- HoloColorPicker
- Butterknife

Fonctionnement de l'application (Les activités principales):
- Main Activity
	Connexion à l'API
	Enregistrement sur l'API
	Dans le menu, il est possible de vider les champs.
	Un easter egg est mis en place: login = Fun! et se connecter directement
	
- Fun Activity
	Possibilité de dessiner des cercles, carrés ou Nyan Cat
	Possibilité de modifier la taille des dessins
	Un dessin ne peut pas être dessiné sur un autre
	Via le menu settigns, on accède à une nouvelle Activité permettant de choisir les couleurs des dessins.
	(Via le HoloColorPicker)
	Les couleurs sont enregistrées dans les Shared Preferences  (possibilité de reset à Bleu et Rouge)
	
-Chat Activity
	Affichage de la liste des messages de l'API
	Chaque utilisateur possède un gravatar
	Un Endless Scroll a été implémenté
	Lorsque des pièces jointes sont présentes, un boutton apparait sous le gravatar pour accéder à une nouvelle Activité affichant les pièces jointes (image, maximum 4)
	Le menu permet d'actualiser ou d'envoyer un nouveau message

-New Message Activity
	Ajout d'un texte de message
	Ajout jusqu'à 4 pièces jointes (via une nouvelle activité).
	
	