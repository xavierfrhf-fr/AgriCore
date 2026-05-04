export interface TransformationRequestDto {
  id?: string;
  product: string;
  desiredQuantity: number;
  partial: boolean;
  bypassStockMin: boolean;
}