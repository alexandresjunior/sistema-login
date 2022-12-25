import { createContext, useEffect, useState } from "react";

export const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState();

    // Verificação feita toda vez que a aplicação for carregada
    useEffect(() => {
        const userToken = localStorage.getItem("user_token");
        const usersStorage = localStorage.getItem("users_db");

        if (userToken && usersStorage) {
            // Verifica se o e-mail do usuário é o mesmo do token
            const hasUser = JSON.parse(usersStorage)?.filter(
                (user) => user.email === JSON.parse(userToken).email
            );

            if (hasUser) setUser(hasUser[0]);
        }

        // TODO: Integração com o backend.
    }, []);

    const signIn = (email, password) => {
        // Obtém os usuários cadastrados no banco de dados
        const usersStorage = JSON.parse(localStorage.getItem("users_db"));

        // Verifica se existe algum e-mail cadastrado igual ao do input
        const hasUser = usersStorage?.filter((user) => user.email === email);

        // Se tiver usuário...
        if (hasUser?.length) {
            // Verifica se ambas as credencias são iguais ao do usuário cadastrado
            if (hasUser[0].email === email && hasUser[0].password === password) {
                const token = Math.random().toString(36).substring(2);

                // Geralmente, é definido apenas o 'token', mas aqui o email também 
                // foi definido para facilitar a identificação do usuário logado.
                localStorage.setItem("user_token", JSON.stringify({ email, token }));

                setUser({ email, password });

                return;
            } else {
                return "E-mail ou senha incorretos";
            }
        } else {
            return "Usuário não cadastrado";
        }
    }

    const signUp = (email, password) => {
        // Obtém os usuários cadastrados no banco de dados
        const usersStorage = JSON.parse(localStorage.getItem("users_db"));

        // Verifica se existe algum e-mail cadastrado igual ao do input
        const hasUser = usersStorage?.filter((user) => user.email === email);

        // Se tiver usuário...
        if (hasUser?.length) {
            return "Já existe uma conta com esse e-mail!";
        }

        let newUser;

        // Concatena o novo usuário aos usuários já cadastrados no banco
        if (usersStorage) {
            newUser = [...usersStorage, { email, password }];
        } else {
            newUser = [{ email, password }];
        }

        localStorage.setItem("users_db", JSON.stringify(newUser));

        return;
    }

    const signOut = () => {
        setUser(null);

        localStorage.removeItem("user_token");
    }

    return (
        <AuthContext.Provider value={{ user, signed: !!user, signIn, signUp, signOut }}>
            {children}
        </AuthContext.Provider>
    )
}
