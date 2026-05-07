export interface AnimalRequest {
	id?: number,
    male: boolean;
	dateNaissance: Date;
	dateVaccination: Date;
	espece: string;
    zoneId: number;
}
