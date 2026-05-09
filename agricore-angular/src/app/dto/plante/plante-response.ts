import { RessourceDataDto } from '../ressource/ressource-data-dto';

export interface PlanteResponse {
    id: number;
    datePlantation: string;
    espece: string;
    nomAffichage:string;
    zoneId: number;
    dernierUpdate: string;
    humidite: number;
    croissance:number;
    mature:boolean;
    pathSprite:string;
    production:number;
    croissanceParSec:number;
    consoEauParMin:number;
    ressource:RessourceDataDto;
}
