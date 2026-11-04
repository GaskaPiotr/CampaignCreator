import { Component, EventEmitter, Input, OnChanges, OnInit, Output, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CampaignRequest, CampaignResponse } from '../../models/campaign.model';
import { TownResponse } from '../../models/town.model';
import { CampaignService } from '../../services/campaign.service';
import { DictionaryService } from '../../services/dictionary.service';

@Component({
  selector: 'app-campaign-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './campaign-form.html',
  styleUrl: './campaign-form.css'
})
export class CampaignFormComponent implements OnInit, OnChanges {
  private readonly fb = inject(FormBuilder);
  private readonly campaignService = inject(CampaignService);
  private readonly dictionaryService = inject(DictionaryService);

  @Input() campaignToEdit: CampaignResponse | null = null;
  @Output() saved = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  form!: FormGroup;
  towns: TownResponse[] = [];
  availableKeywords: string[] = [];
  selectedKeywords: string[] = [];
  errorMessage: string | null = null;

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required],
      bidAmount: [null, [Validators.required, Validators.min(0.01)]],
      campaignFund: [null, [Validators.required, Validators.min(0.01)]],
      status: ['ON', Validators.required],
      townId: [null],
      radiusKm: [null, [Validators.required, Validators.min(1)]]
    });

    this.dictionaryService.getTowns().subscribe(t => this.towns = t);
    this.dictionaryService.getKeywords().subscribe(k => this.availableKeywords = k);
  }

  ngOnChanges(): void {
    if (this.campaignToEdit && this.form) {
      this.form.patchValue(this.campaignToEdit);
      this.selectedKeywords = [...this.campaignToEdit.keywords];
    }
  }

  addKeyword(input: HTMLInputElement): void {
    const val = input.value.trim().toLowerCase();
    if (val && this.availableKeywords.includes(val) && !this.selectedKeywords.includes(val)) {
      this.selectedKeywords.push(val);
      input.value = '';
    }
  }

  removeKeyword(kw: string): void {
    this.selectedKeywords = this.selectedKeywords.filter(k => k !== kw);
  }

  onSubmit(): void {
    if (this.form.invalid || this.selectedKeywords.length === 0) {
      this.errorMessage = 'Fill all required fields and select at least one keyword.';
      return;
    }

    const payload: CampaignRequest = {
      ...this.form.value,
      keywords: this.selectedKeywords,
      townId: this.form.value.townId ? Number(this.form.value.townId) : null
    };

    const action$ = this.campaignToEdit
      ? this.campaignService.updateCampaign(this.campaignToEdit.id, payload)
      : this.campaignService.createCampaign(payload);

    action$.subscribe({
      next: () => {
        this.reset();
        this.saved.emit();
      },
      error: (err) => this.errorMessage = err.error?.message || 'Action failed.'
    });
  }

  reset(): void {
    this.form.reset({ status: 'ON', townId: null });
    this.selectedKeywords = [];
    this.errorMessage = null;
    this.cancelled.emit();
  }
}