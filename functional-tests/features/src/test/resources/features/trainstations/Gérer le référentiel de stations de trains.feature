# language: fr
Fonctionnalité: Gérer le référentiel de stations de trains

  Scénario: Créer et récupérer les stations de trains
    Étant données les stations de trains à créer :
      | Identifiant | Nom        |
      | STATION_A   | East Blue  |
      | STATION_B   | North Blue |
      | STATION_C   | Grand Line |
    Lorsque j'enregistre ces stations de trains
    Et que je récupère le référentiel des stations de trains
    Alors je reçois ces stations de trains

  Scénario: Tenter d'enregistrer deux stations de trains avec le même identifiant
    Étant données les stations de trains à créer :
      | Identifiant | Nom        |
      | STATION_A   | East Blue  |
      | STATION_A   | North Blue |
    Lorsque je tente d'enregistrer ces stations de trains
    Alors je reçois une erreur 400 avec le message "L'identifiant des stations de trains doit être unique"
