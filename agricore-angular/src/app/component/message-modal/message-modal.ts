import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  Output,
  SimpleChanges,
} from '@angular/core';
import { Message } from '../../model/message';
import { NgStyle } from '@angular/common';

@Component({
  selector: 'app-message-modal',
  imports: [NgStyle],
  templateUrl: './message-modal.html',
  styleUrl: './message-modal.css',
})
export class MessageModal implements OnChanges, OnDestroy {
  @Input({ required: true }) message!: Message;

  @Output() closeEvent = new EventEmitter<void>();

  private timeoutId?: ReturnType<typeof setTimeout>;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['message']) {
      this.clearCurrentTimeout();
      this.startTimeoutIfNeeded();
    }
  }

  ngOnDestroy(): void {
    this.clearCurrentTimeout();
  }

  close(): void {
    this.clearCurrentTimeout();
    this.closeEvent.emit();
  }

  protected getPos(): Record<string, string> {
    return {
      top: this.message.position?.top ?? '22%',
      left: this.message.position?.left ?? '45%',
    };
  }

  private startTimeoutIfNeeded(): void {
    if (this.message.timeout && this.message.timeout > 0) {
      this.timeoutId = setTimeout(() => {
        this.closeEvent.emit();
      }, this.message.timeout);
    }
  }

  private clearCurrentTimeout(): void {
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
      this.timeoutId = undefined;
    }
  }
}
