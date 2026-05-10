export interface Message {
  message: string;
  type: 'message' | 'warning' | 'error';
  timeout?: number;
}
