import { AuthProvider } from './contexts/AuthContext';
import RoutesApp from './routes';
import GlobalStyle from './styles';

const App = () => {
  return (
    <AuthProvider>
      <RoutesApp />
      <GlobalStyle />
    </AuthProvider>
  );
}

export default App;
