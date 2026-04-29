export interface TransformationRequestDto {
  product: string;
  desiredQuantity: number;
  partial: boolean;
  bypassStockMin: boolean;
}