export interface AppDateProps {
  dateFromNow?: boolean;
  dateFrom?: Date;
  dateToNow?: boolean;
  dateTo?: Date;
  format?: string;
  exclusiveRules?: boolean;
}

export const AppDateDefaultProps = {
  dateFromNow: false,
  dateToNow: false,
  format: 'YYYY-MM-DD',
  exclusiveRules: false,
};
