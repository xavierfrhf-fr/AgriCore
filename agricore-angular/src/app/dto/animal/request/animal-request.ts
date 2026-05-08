export interface AnimalRequest {
	id?: number, //Peut être null pour le create
    male: boolean;
	dateNaissance: Date;
	dateVaccination: Date;
	espece: string;
    zoneId?: number; //Peut être null pour le create
}
