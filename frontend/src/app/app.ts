import { Component, ViewChild } from '@angular/core';
import { EmeraldBalanceComponent } from './components/emerald-balance/emerald-balance';
import { CampaignFormComponent } from './components/campaign-form/campaign-form';
import { CampaignListComponent } from './components/campaign-list/campaign-list';
import { CampaignResponse } from './models/campaign.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    EmeraldBalanceComponent,
    CampaignFormComponent,
    CampaignListComponent
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  @ViewChild(CampaignListComponent) campaignList!: CampaignListComponent;

  selectedCampaign: CampaignResponse | null = null;

  onEditRequested(campaign: CampaignResponse): void {
    this.selectedCampaign = campaign;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  onFormSaved(): void {
    this.selectedCampaign = null;
    this.campaignList?.loadCampaigns();
  }

  onFormCancelled(): void {
    this.selectedCampaign = null;
  }
}