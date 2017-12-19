## Coach Santé  --- Perrine Pullicino

### Installation

1. Via l'apk fournie :
    Il est nécessaire d'**activer les sources inconnues**, pour pouvoir installer l'application.       

2. Import dans Android Studio : Android Studio fera le travail :)


### Premier Contact

#### Premier Lancement :
S'il s'agit de votre **tout premier lancement de l'application**, l'écran d'accueil habituel ne s'affichera pas, mais vous trouverez à la place l'écran **"Profil Utilisateur"**. Il vous demande d'entrer quelques données :
- Votre nom d'utilisateur, qui doit uniquement **ne pas être vide**.
- Votre minimum désiré d'apport de calories par jour, **qui doit être un nombre**.
- Votre maximum désiré d'apport de calories par jour, **qui doit être un nombre**.
- Votre poids, qui est optionnel, et **qui doit être un nombre**, 0 si non désiré.

Vous pouvez maintenant confirmer vos informations, ne vous inquiétez pas, vous pourrez les modifier !


#### Ecran principal :

Vous voilà enfin sur l'écran principal. **4 boutons sont disponibles**, et un cinquième est "**caché**" dans le menu de la toolbar. Dans l'ordre :

- ##### Le bouton de **renseignement de repas** :
Il vous amène sur **l'écran de sélection de nourriture**, et vous permet de sélectionner ce que vous avez **mangé** pendant votre repas. Vous cochez la nourriture correspondante, et vous indiquez également **votre portion consommée**. Par défaut, vous en mangez une portion, mais la possibilité d'en manger une demie ou un peu plus qu'une est offerte, ainsi que plusieurs si vous vous sentiez en appetit ;)

- ##### Le bouton du **Profil**
Vous trouverez ici les renseignements fournis au lancement de l'application et vous avez la possibilité de les modifier. En outre, deux boutons sont maintenant utilisables ! Nous les détaillerons un peu plus bas.

- ##### Le bouton de **Review** :
Vous pouvez ici voir vos repas de la semaine, présentés avec la Date et l'heure correspondante, le total de calories du repas, les aliments consommés et la portion respective de chaque aliment. Vous avez un bouton "supprimer" à votre disposition s'il y a eu erreur.

- ##### Le bouton de **base de données de aliments**:
Ici, vous pouvez ajouter un aliment à la base de données, ou modifier les aliments présents. Vous avez besoin du **nom de l'aliment** et des **calories pour une portion qui vous correspond**. Par exemple, si vous consommez 100g de riz en général, vous devriez entrer les calories pour 100g de riz. Vous appuyez ensuite sur Add/Ajouter, et le tour est joué. Pour **modifier** un aliment, faites glisser son slider de calories. Vous pouvez modifier **plusieurs** éléments à la fois. Appuyez ensuite sur Update. Enfin, vous pouvez **supprimer** un ou plusieurs éléments (à la fois) en cochant les aliments désirés et en appuyant bien sûr sur **supprimer**.

- ##### **Le bouton "caché" des notifications** :

Oui, vous pouvez enregistrer une **heure** pour **recevoir une notification (chaque jour)** de l'application qui vous dit de lui renseigner vos aliments consommés. Si vous appuyez sur la notification, elle s'en va et vous emmène **directement à la page des repas** !


#### Voir son progrès :

Nous en parlions, il y a **deux boutons** disponibles dans le **profil de l'utilisateur** :
- Bar Chart correspond à un graphique en bâtons permettant de voir si la semaine respectait les objectifs fixés. Un jour où l'on a trop mangé est représenté en rouge, avec le total de calories qui s'affiche, un jour où l'on a "bien" mangé est représenté en vert et un jour où l'on a pas assez mangé est représenté en bleu.
- Pie Chart est un diagramme en camembert représentant la consommation de chaque aliment. Les portions consommées sont indiquées, plutôt que les pourcentages, cela semblait plus approprié. Bien entendu, si un ingrédient n'a **pas** été consommé, il n'apparait pas. Il est bon de noter qu'il s'agit de la consommation des ingrédients depuis le début de l'application, et non ceux de la semaine. L'utilisation d'un tel diagramme peut être de voir quels aliments nous consommons le plus pour pouvoir par exemple faire une liste de courses.


###### Langues
L'application est disponible en Français et en anglais, il suffit de changer la langue de votre téléphone pour avoir accès à l'une ou l'autre version.




#### Possibles extensions

###### Gestion de ce qui se passe quand on tourne l'écran
Bien que cela ne pose aucun problème sur la plupart des écrans de l'application, les deux écrans possédant des listView (c'est à dire l'écran de base de données de nourriture et l'écran de sélection de repas) n'agissent pas de la bonne façon quand on tourne l'écran. Ainsi, ils ne gardent pas en mémoire les aliments cochés, par exemple.

###### Calcul de l'IMC
Vu que nous demandons déjà le poids de l'utilisateur, si nous lui demandions sa taille également, nous pourrions calculer son IMC.


###### Git Hub

https://github.com/Phoenixton/CoachSante
