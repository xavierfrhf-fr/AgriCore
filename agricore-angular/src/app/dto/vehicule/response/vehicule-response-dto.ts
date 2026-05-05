export interface VehiculeResponseDTO {
  id: number;
  typeVehicule: string;
  dateControleTech: string;  
  delaiAvantControle: number;
  carburantActuel: number;
  zoneId: number;
  zoneNom: string;
}