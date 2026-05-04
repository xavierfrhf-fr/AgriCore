export interface RessourceRequestDto {
  id?: string;  // présent uniquement pour PUT/PATCH, absent à la création
  nom: string;
  quantite: number;
  stockMin: number;
  prixLot: {prixPar: number; quantiteLot: number; unite: string;};
}