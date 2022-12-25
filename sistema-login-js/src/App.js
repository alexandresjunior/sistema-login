import { AuthContext } from './contexts/AuthContext';
import RoutesApp from './routes';
import GlobalStyle from './styles';

const App = () => {
  return (
    <AuthContext.Provider>
      <RoutesApp />
      <GlobalStyle />
    </AuthContext.Provider>
  );
}

export default App;
