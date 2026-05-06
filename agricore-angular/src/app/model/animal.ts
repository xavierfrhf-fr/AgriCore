export interface Animal {
    id: number;
    male: boolean;
    dateNaissance: Date;
    dateVaccination: Date;
    espece: string;
    zone: string;
    pathSprite: string;
    nomAffichage: string;
    delaisVaccination: number;
}
