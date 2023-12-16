# language: fr
Fonctionnalité: Gérer le référentiel de stations de trains

  Scénario: Mettre à jour les stations de train
    Étant données les stations de trains suivantes à créer :
      | Identifiant | Nom        |
      | STATION_A   | East Blue  |
      | STATION_B   | North Blue |
      | STATION_C   | Grand Line |
    Lorsque j'enregistre ces stations de trains
    Alors ces stations de trains sont enregistrées avec succès
