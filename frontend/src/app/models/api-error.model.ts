export interface ApiErrorResponse {
  status: number;
  message: string;
  timestamp: string;
  fieldErrors?: Record<string, string>;
}