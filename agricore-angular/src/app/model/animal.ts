export interface Animal {
    id: number;
    espece: string;
    male: boolean;
    dateNaissance: Date;
    dateVaccination: Date;
    pathSprite: string;
    nomAffichage: string;
    delaisVaccination: number;
    zone: string;
    zonePathSprite: string; 
    produit: string;
    produitPathSprite: string;
}
