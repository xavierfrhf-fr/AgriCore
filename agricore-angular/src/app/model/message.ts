import { MessagePosition } from './message-position';

export interface Message {
  message: string;
  type: 'message' | 'warning' | 'error';
  timeout?: number;
  position?:MessagePosition;
}
