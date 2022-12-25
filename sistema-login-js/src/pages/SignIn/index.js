import React, { useState } from "react";
import Input from "../../components/Input";
import Button from "../../components/Button";
import * as C from "./styles";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";

const SignIn = () => {
    const { signIn } = useAuth();
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [error, setError] = useState("");

    const handleLogin = () => {
        if (!email | !senha) {
            setError("Preencha todos os campos");
            return;
        }

        const res = signIn(email, senha);

        if (res) {
            setError(res);
            return;
        }

        navigate("/home");
    };

    return (
        <C.Container>
            <C.Label>SISTEMA DE LOGIN</C.Label>
            <C.Content>
                <Input
                    type="email"
                    placeholder="Digite seu E-mail"
                    value={email}
                    onChange={(e) => [setEmail(e.target.value), setError("")]}
                />
                <Input
                    type="password"
                    placeholder="Digite sua Senha"
                    value={senha}
                    onChange={(e) => [setSenha(e.target.value), setError("")]}
                />
                <C.labelError>{error}</C.labelError>
                <Button text="Entrar" onClick={handleLogin} />
                <C.LabelSignUp>
                    Não tem uma conta?
                    <C.Strong>
                        <Link to="/sign-up">&nbsp;Registre-se</Link>
                    </C.Strong>
                </C.LabelSignUp>
            </C.Content>
        </C.Container>
    );
};

export default SignIn;