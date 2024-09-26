import { ValidationRule } from 'quasar';

export function useAppValidation() {
  return {
    requireRule(): ValidationRule {
      return (v) => !!v;
    },
  };
}
