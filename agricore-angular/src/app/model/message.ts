import { MessagePosition } from './message-position';

export interface Message {
  message: string;
  type: 'message' | 'warning' | 'error' | 'success';
  timeout?: number;
  position?:MessagePosition;
}
