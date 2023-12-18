# language: fr
Fonctionnalité: Récupérer les horaires de trains d'une station de trains

  Scénario: Récupérer les horaires de trains d'une station de trains
    Étant donnée une station de train existante
    Et que la fiche horaire du jour de cette station de trains est disponible chez le partenaire
    Lorsque je récupère les prochains départs de cette station de trains
    Alors je reçois les prochains départs de cette station de trains
