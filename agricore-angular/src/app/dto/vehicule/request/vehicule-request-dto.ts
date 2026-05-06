export interface VehiculeRequestDTO {
  id?: number;  // présent uniquement pour PUT, absent à la création
  typeVehicule: string;
  dateControleTech: string;  // ISO date string
  carburantActuel: number;
  zoneId: number;
}