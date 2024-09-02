package com.matriculas.matriculas.Dados;

public interface IPersistencia<T> {
    void salvar(T objeto);
    T carregar(Object id);
    void atualizar(T objeto);
    void remover(Object id);
}
