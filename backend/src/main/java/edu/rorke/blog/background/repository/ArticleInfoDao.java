package edu.rorke.blog.background.repository;

import edu.rorke.blog.background.entity.ArticleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/4/6 13:51
 */
@Repository
public interface ArticleInfoDao extends JpaRepository<ArticleInfo,Integer>, JpaSpecificationExecutor<ArticleInfo> {
    /**
     * 查找没有被删除的文章
     * @param isDelete 是否被删除，0=false,1=true
     * @param pageable 分页
     * @return  分页列表
     */
    Page<ArticleInfo> findAllByIsDeleteLike(int isDelete, Pageable pageable);

    /**
     * 根据文章id寻找文章信息
     * @param articleId 文章id
     * @param isDelete 是否被删除，0=false,1=true
     * @return null or 文章信息
     */
    Optional<ArticleInfo> findByArticleIdAndIsDelete(int articleId,int isDelete);
}
